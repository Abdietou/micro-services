package org.devabdi.secservice

import org.devabdi.secservice.config.ParamsConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.ConfigurableApplicationContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(ParamsConfig::class)
class SecServiceApplication

private const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
private const val ANSI_RESET = "\u001B[0m"
private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
private val logger: Logger = LoggerFactory.getLogger(SecServiceApplication::class.java)

fun main(args: Array<String>) {
    val springApplication = SpringApplication(SecServiceApplication::class.java)
    val ctx: ConfigurableApplicationContext = springApplication.run(*args)

    val applicationName = ctx.environment.getProperty("spring.application.name")
    val applicationVersion = ctx.environment.getProperty("app.version")
    val applicationPort = ctx.environment.getProperty("server.port")
    val formattedDate = dateTimeFormatter.format(LocalDateTime.now())

    logger.info(
        "{} {} {} v{} STARTED SUCCESSFULLY ON PORT {} {}",
        ANSI_GREEN_BACKGROUND, formattedDate, applicationName, applicationVersion, applicationPort, ANSI_RESET
    )

    //TODO: test to remove
    logger.info("Global Params: p1:{}, p2:{}", ctx.environment.getProperty("global.params.p1"), ctx.environment.getProperty("global.params.p1"))

}
