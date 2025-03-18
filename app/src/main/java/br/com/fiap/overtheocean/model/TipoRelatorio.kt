package br.com.fiap.overtheocean.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Enum para os tipos de relatório
enum class TipoRelatorio(val descricao: String) {
    AVISTAMENTO_ANIMAL("Avistamento de animais marinhos"),
    OCORRENCIA_AMBIENTAL("Ocorrência ambiental"),
    BOA_ACAO("Boa ação")
}

// Entidade para o Room
@Entity(tableName = "relatorios")
data class Relatorio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tipo: TipoRelatorio?,
    val descricao: String,
    val caminhoImagem: String,  // Caminho para a imagem armazenada
    val dataHora: Date = Date()  // Data e hora do relatório
)