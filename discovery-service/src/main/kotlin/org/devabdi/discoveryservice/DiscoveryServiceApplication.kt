package org.devabdi.discoveryservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.context.ConfigurableApplicationContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableEurekaServer
class DiscoveryServiceApplication

private const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
private const val ANSI_RESET = "\u001B[0m"
private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
private val logger: Logger = LoggerFactory.getLogger(DiscoveryServiceApplication::class.java)

fun main(args: Array<String>) {
    val springApplication = SpringApplication(DiscoveryServiceApplication::class.java)
    val ctx: ConfigurableApplicationContext = springApplication.run(*args)

    val applicationName = ctx.environment.getProperty("spring.application.name")
    val applicationVersion = ctx.environment.getProperty("app.version")
    val applicationPort = ctx.environment.getProperty("server.port")
    val formattedDate = dateTimeFormatter.format(LocalDateTime.now())

    logger.info(
        "{} {} {} v{} STARTED SUCCESSFULLY ON PORT {} {}",
        ANSI_GREEN_BACKGROUND, formattedDate, applicationName, applicationVersion, applicationPort, ANSI_RESET
    )
}
