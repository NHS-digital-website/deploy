import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

/**
Use this for navigating around the system (searches, publication, datasets,
series etc) but with pauses included to mimic real user behaviour.
*/
class BrowsingWithPausesLoadTest {

	val scn = scenario("Browsing with pauses load test")
		.exec(http("Random 1 - Home")
		.get("/"))
		.pause(1)
		.exec(http("Random 2 - Search health")
		.get("/search?query=health"))
		.pause(3)
		.exec(http("Random 3 - Publication")
			.get("/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-episodes-admissions"))
		.pause(2)
		.exec(http("Random 4 - Download")
			.get("/binaries/content/documents/corporate-website/publication-system/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/publicationsystem%3AFiles-v3/publicationsystem%3AattachmentResource")
			.check(status.is(200)))
		.pause(1)
		.exec(http("Random 5 - Terms and Conditions")
			.get("/about/terms-and-conditions"))
		.pause(1)
		.exec(http("Random 6 - Search hospital")
			.get("/search?query=hospital"))
		.pause(2)
		.exec(http("Random 7 - Data set")
			.get("/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-episodes-admissions/hospital-episodes-admissions-accidents-directly-standardised-rate-15-64-years-annual-trend-mfp"))
}
