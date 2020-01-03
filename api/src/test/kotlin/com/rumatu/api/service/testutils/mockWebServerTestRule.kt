package com.rumatu.api.service.testutils

import okhttp3.mockwebserver.MockWebServer

fun mockWebServerInit(server: MockWebServer) =
    before {
        server.start()
    } after {
        server.shutdown()
    }
