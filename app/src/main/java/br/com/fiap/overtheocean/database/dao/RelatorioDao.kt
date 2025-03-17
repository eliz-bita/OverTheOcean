package br.com.fiap.overtheocean.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.overtheocean.model.Relatorio
import kotlinx.coroutines.flow.Flow


@Dao
interface RelatorioDao {
    @Insert
    suspend fun inserir(relatorio: Relatorio): Long

    @Query("SELECT * FROM relatorios ORDER BY dataHora DESC")
    fun obterTodos(): Flow<List<Relatorio>>

    @Query("SELECT * FROM relatorios WHERE id = :id")
    suspend fun obterPorId(id: Long): Relatorio?

    @Delete
    suspend fun excluir(relatorio: Relatorio)
}

