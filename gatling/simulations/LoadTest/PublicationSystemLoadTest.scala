
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PublicationSystemLoadTest {

	/**
		x1 /publications
		x2 series document
		x4 publication document
		x4 dataset document
	*/
	var series = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current"
	)
	var publications = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-episodes-admissions",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-hospital-admissions-and-timely-surgery",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-diabetes",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-suicide-and-injury-undetermined"
	)
	var datasets = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-episodes-admissions/hospital-episodes-admissions-accidents-directly-standardised-rate-15-64-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-hospital-admissions-and-timely-surgery/emergency-hospital-admissions-and-timely-surgery-fractured-proximal-femur-indirectly-standardised-percent-all-ages-annual-trend-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-diabetes/mortality-from-diabetes-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-suicide-and-injury-undetermined/mortality-from-suicide-and-injury-undetermined-indirectly-standardised-ratio-smr-15-44-years-3-year-average-mfp"
	)

	var searches = List(
		"/search?query=compendium",
		"/search/DOCUMENT+TYPE/publicationsystem_x003a_series?query=compendium"
	)

	var executions = Seq(
		exec(http("/publications").get("/publications"))
	)

	series.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"series document #$i").get(el) )
	} }

	publications.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"publications document #$i").get(el) )
	} }

	datasets.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"datasets document #$i").get(el) )
	} }

	searches.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"search #$i").get(el) )
	} }

	val scn = scenario("PublicationSystemLoadTest").exec(executions)
}
