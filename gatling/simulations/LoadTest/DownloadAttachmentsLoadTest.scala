
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DownloadAttachmentsLoadTest {

	val scn = scenario("DownloadAttachmentsLoadTest")
		.exec(http("download #1")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/shmi/current/statistical-model-data/statistical-model-data/publicationsystem%3AFiles-v3%5B4%5D/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
		.exec(http("download #2")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hypertensive-disease/mortality-from-hypertensive-disease-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-hypertensive-disease-crude-death-rate-by-age-group-3-year-average-mfp/publicationsystem%3AFiles-v3%5B2%5D/publicationsystem%3AattachmentResource")
			.check(status.is(200))
		)
}
