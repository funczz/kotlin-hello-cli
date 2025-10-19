import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ProjectConfigTest {

    @Test
    fun `ProjectConfig has a 'projectName' variable`() {
        val actual = ProjectConfig.projectName
        System.err.println("ProjectConfig.projectName: $actual")
        assertEquals("hello", actual)
    }

    @Test
    fun `ProjectConfig has a 'parentName' variable`() {
        val actual = ProjectConfig.parentName
        System.err.println("ProjectConfig.parentName: $actual")
        assertEquals("kotlin-hello-cli", actual)
    }

    @Test
    fun `ProjectConfig has a 'tmpdir' variable`() {
        val actual = ProjectConfig.tmpdir?.canonicalPath
        System.err.println("ProjectConfig.tmpdir: $actual")
        assertNotNull(actual)
    }

    @Test
    fun `ProjectConfig has a 'createTempDirectory' method`() {
        val actual = ProjectConfig.createTempDirectory().canonicalPath
        System.err.println("ProjectConfig::createTempDirectory: $actual")
        assertTrue {
            Regex(""".+_[0-9]+$""").matches(actual)
        }
    }

    @Test
    fun `ProjectConfig has a 'createTempFile' method`() {
        val actual = ProjectConfig.createTempFile().canonicalPath
        System.err.println("ProjectConfig::createTempFile: $actual")
        assertTrue {
            Regex(""".+_[0-9]+\.temp$""").matches(actual)
        }
    }
}