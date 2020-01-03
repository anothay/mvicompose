package com.rumatu.api.service

import com.rumatu.api.api.PATH_COMMENTS
import com.rumatu.api.api.PATH_POSTS
import com.rumatu.api.api.PATH_USERS
import com.rumatu.api.di.apiModule
import com.rumatu.api.service.testutils.extensions.getStringFromResource
import com.rumatu.api.service.testutils.mockNetworkModule
import com.rumatu.api.service.testutils.mockWebServerInit
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class ApiServiceTest : KoinTest {

    private val api: ApiService by inject()
    private val server: MockWebServer = MockWebServer()

    @get:Rule
    val mockServerRule = mockWebServerInit(server)

    @Before
    fun setUp() {
        startKoin {
            modules(
                listOf(
                    mockNetworkModule(server),
                    apiModule
                )
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getComments() {
        server.enqueue(TestData.getMockResponse("api/Comments.json"))

        api.getComments()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                this[0].id shouldEqualTo 1
                this[1].name shouldBeEqualTo "quo vero reiciendis velit similique earum"
                this[2].email shouldBeEqualTo "Nikita@garfield.biz"
                this[3].body shouldBeEqualTo "non et atque\noccaecati deserunt quas " +
                        "accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur " +
                        "itaque dolor\net qui rerum deleniti ut occaecati"
            }

        server.takeRequest().path shouldBeEqualTo "/$PATH_COMMENTS"
    }

    @Test
    fun getUsers() {
        server.enqueue(TestData.getMockResponse("api/Users.json"))

        api.getUsers()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                val (userName, id) = this[0]
                userName shouldBeEqualTo "Bret"
                id shouldEqualTo 1
            }

        server.takeRequest().path shouldBeEqualTo "/$PATH_USERS"
    }

    @Test
    fun getPosts() {
        server.enqueue(TestData.getMockResponse("api/Posts.json"))

        api.getPosts()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                this[0].userId shouldEqualTo 1
                this[1].id shouldEqualTo 2
                this[2].title shouldBeEqualTo "ea molestias quasi exercitationem" +
                        " repellat qui ipsa sit aut"
                this[3].body shouldBeEqualTo "ullam et saepe reiciendis voluptatem " +
                        "adipisci\nsit amet autem assumenda provident rerum culpa\nquis " +
                        "hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt " +
                        "voluptatem rerum illo velit"
            }

        server.takeRequest().path shouldBeEqualTo "/$PATH_POSTS"
    }

    object TestData {

        fun getMockResponse(json: String, requestCode: Int = 200) = MockResponse()
            .setResponseCode(requestCode)
            .setBody(getStringFromResource(json))
    }
}
