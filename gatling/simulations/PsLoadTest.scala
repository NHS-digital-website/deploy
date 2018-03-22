
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PsLoadTest extends Simulation {

	println("baseUrl: ".concat(Config.host))

	val MAX_RESPONSE_TIME = 5000
	val INJECT_USERS_PER_SEC = 1
	val INJECT_USERS_DURING_SECONDS = 60

	val httpProtocol = http
		.baseURL(Config.host)
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
		.headers(Map("Upgrade-Insecure-Requests" -> "1"))
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en,pl;q=0.9,en-US;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")

	val publicationSystem = new PublicationSystemLoadTest()
	val downloads = new DownloadAttachmentsLoadTest()
	var browsingWithPauses = new BrowsingWithPausesLoadTest()

	// Adjust the value of constantUsersPerSec accordingly.
	// Note: 1 user will perfrom around 150 requests based on the links
  	// defined in PublicationSystemLoadTest and DownloadAttachmentsLoadTest
	setUp(List(
		publicationSystem.scn.inject(
			constantUsersPerSec(INJECT_USERS_PER_SEC) during(INJECT_USERS_DURING_SECONDS seconds) randomized,
		),
		downloads.scn.inject(
			constantUsersPerSec(INJECT_USERS_PER_SEC) during(INJECT_USERS_DURING_SECONDS seconds) randomized,
		)
	)).protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(MAX_RESPONSE_TIME),
		global.failedRequests.count.is(0)
	)
}
