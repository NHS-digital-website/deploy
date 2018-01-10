
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DownloadAttachmentsLoadTest {

	val scn = scenario("DownloadAttachmentsLoadTest")
		.exec(http("download #1")
			.get("/binaries/content/documents/corporate-website/publication-system/prescribing-costs/prescribing-costs-in-hospitals-and-the-community-england-2015-16/prescribing-costs-in-hospitals-and-the-community-england-2015-16-nice-data/prescribing-costs-in-hospitals-and-the-community-england-2015-16-nice-data/publicationsystem%3AFiles-v3/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
		.exec(http("download #2")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-cancer/mortality-from-stomach-cancer-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-stomach-cancer-directly-standardised-rate-all-ages-annual-trend-mfp/publicationsystem%3AFiles-v3%5B3%5D/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
}
