import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object ProjectConfig {

    val projectName: String = File(".").canonicalFile.name

    val parentName: String = File("..").canonicalFile.name

    val tmpdir: File? = Paths.get(System.getProperty("java.io.tmpdir")).toFile().canonicalFile

    fun createTempDirectory(file: File = tmpdir!!, prefix: String = "${projectName}_"): File {
        return Files.createTempDirectory(file.toPath(), prefix).toFile()
    }

    fun createTempFile(file: File = tmpdir!!, prefix: String = "${projectName}_", suffix: String = ".temp"): File {
        return Files.createTempFile(file.toPath(), prefix, suffix).toFile()
    }
}