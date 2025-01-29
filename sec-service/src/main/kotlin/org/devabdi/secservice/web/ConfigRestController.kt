package org.devabdi.secservice.web

import org.devabdi.secservice.config.ParamsConfig
import org.devabdi.secservice.utils.SecConstants
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RefreshScope
@RequestMapping(SecConstants.API_SERVICE_PATH)
class ConfigRestController(private val paramsConfig: ParamsConfig) {

    @GetMapping(SecConstants.GLOBAL_CONFIG_URI)
    fun globalConfig(): ParamsConfig {
        return paramsConfig;
    }
}