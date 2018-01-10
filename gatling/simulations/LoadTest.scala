
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadTest extends Simulation {
	var baseUrl = "http://hippo-delivery.web.dev.ps.nhsd.io"
	val httpProtocol = http
		.baseURL(baseUrl)
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
		.headers(Map("Upgrade-Insecure-Requests" -> "1"))
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en,pl;q=0.9,en-US;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")

	val publicationSystem = new PublicationSystemLoadTest()
	val downloads = new DownloadAttachmentsLoadTest()

	setUp(List(
		publicationSystem.scn.inject(
			rampUsers(150) over(60 seconds),
			rampUsers(300) over(60 seconds),
			rampUsers(500) over(60 seconds),
			rampUsers(800) over(60 seconds),
			rampUsers(1200) over(60 seconds),
			rampUsers(1600) over(60 seconds),
			rampUsers(2000) over(60 seconds)
		),
		downloads.scn.inject(
			rampUsers(100) over(600 seconds)
		)
	)).protocols(httpProtocol)
}
