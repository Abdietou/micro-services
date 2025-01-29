package org.devabdi.secservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "global.params")
class ParamsConfig {
    var p1: Int = 0
    var p2: Int = 0
}