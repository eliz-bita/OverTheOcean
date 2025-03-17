package br.com.fiap.overtheocean.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import br.com.fiap.overtheocean.database.dao.AppDatabase
import br.com.fiap.overtheocean.model.Relatorio
import br.com.fiap.overtheocean.model.TipoRelatorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RelatorioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val relatorioDao = database.relatorioDao()

    val todosRelatorios: Flow<List<Relatorio>> = relatorioDao.obterTodos()

    suspend fun salvarRelatorio(tipo: TipoRelatorio, descricao: String, uri: Uri?) {
        if (uri != null) {
            val caminhoImagem = salvarImagem(getApplication(), uri)
            val relatorio = Relatorio(
                tipo = tipo,
                descricao = descricao,
                caminhoImagem = caminhoImagem
            )
            relatorioDao.inserir(relatorio)
        }
    }

    private suspend fun salvarImagem(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(imageFileName, ".jpg", storageDir)

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return@withContext file.absolutePath
    }
}
