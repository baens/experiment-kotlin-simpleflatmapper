import org.simpleflatmapper.jdbc.JdbcMapperFactory
import java.sql.DriverManager
import java.util.stream.Stream

fun main(args: Array<String>) {
    Class.forName("org.postgresql.Driver")

    for(i in 1..10) {
        getData().use {
            it.forEach { println(it) }
        }
    }

    println("holding")
    readLine()
}

// Data class representing a row
data class DataRow(val id: Int, val data: String)

val mapper = JdbcMapperFactory.newInstance().newMapper(DataRow::class.java)

fun getData() : Stream<DataRow> {
    val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password")

    val statement = connection.prepareStatement("SELECT id, data FROM test")

    val resultSet = statement.executeQuery()

    return mapper.stream(resultSet)
                 .onClose {
                    println("closing connection")
                    resultSet.close()
                    statement.close()
                    connection.close()
                 }
}