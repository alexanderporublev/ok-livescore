package ru.otus.otuskotlin.marketplace.blackbox

import org.testcontainers.containers.DockerComposeContainer
import java.io.File
import kotlin.io.println
@Suppress("unused")
class AppCompose private constructor(

) {
    init {
        println("in")
    }
    private val _service = "app_1"
    private val _port = 80

    private val compose =
        DockerComposeContainer(File("../deploy/docker-compose-app.yml")).apply {
            withExposedService(
                _service,
                _port,
//                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30))
            )
            withLocalCompose(true)
            start()
        }

    val hostApp: String = compose.getServiceHost(_service, _port)

    val portApp: Int = compose.getServicePort(_service, _port)

    fun close() {
        compose.close()
    }

    companion object {
        val C by lazy { println("before")
              AppCompose()
        }
    }
}
