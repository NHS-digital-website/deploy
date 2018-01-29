
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DownloadAttachmentsLoadTest {

	val scn = scenario("DownloadAttachmentsLoadTest")
		.exec(http("download #1")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-12-1-percentage-uptake-of-influenza-immunisation-in-people-aged-over-65/lboi-indicator-12-1-percentage-uptake-of-influenza-immunisation-in-people-aged-over-65/publicationsystem%3AFiles-v3/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
		.exec(http("download #2")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/publicationsystem%3AFiles-v3/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
}
