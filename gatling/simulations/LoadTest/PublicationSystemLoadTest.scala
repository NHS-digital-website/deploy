
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PublicationSystemLoadTest {

	/**
	x1 /publications
		x2 series document (24)
		x4 publication document (48)
		x8 dataset document (100)
	  */
	var series = List(
		"/data-and-information/publications/statistical/statistics-on-alcohol",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/circulatory-diseases",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/dental-health",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/alcohol-consumption",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-breast-cancer",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/general-health",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/chromosomal-abormalities-congential-malformations",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-land-transport-accidents",
		"/data-and-information/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/archive",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/deaths-at-home",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/obesity-nutrition",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/area-classification-including-deprivation",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-chronic-liver-disease",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/education",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/archive/emergency-readmissions-to-hospital-within-28-days-of-discharge",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/abortions",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/social-care",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-accidents",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/births",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-gastric-duodenal-and-peptic-ulcers",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/archive/hospital-episodes-admissions",
	)
	var publications = List(
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/returning-to-usual-place-of-residence",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/obesity-nutrition",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-respiratory-diseases",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-rheumatic-heart-disease",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-breast-cancer",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/abortions",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stroke",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-admissions",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/respiratory-diseases",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hypertensive-disease",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-oesophageal-cancer",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-lung-cancer",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-fracture-of-femur",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-hospital-admissions-and-timely-surgery",
		"/data-and-information/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current",
		"/data-and-information/publications/clinical-indicators/nhs-outcomes-framework/current",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/immunisations-and-infectious-diseases",
		"/data-and-information/publications/clinical-indicators/ccg-outcomes-indicator-set/current"
	)
	var datasets = List(
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-infectious-diseases/mortality-from-tuberculosis-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-tuberculosis-crude-death-rate-by-age-group-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hodgkin-s-disease/mortality-from-hodgkin-s-disease-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-hodgkin-s-disease-crude-death-rate-by-age-group-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-hysterectomy-indirectly-standardised-percent-all-ages-annual-trend-f/emergency-readmissions-to-hospital-within-28-days-of-discharge-hysterectomy-indirectly-standardised-percent-all-ages-annual-trend-f",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-number-by-age-group-annual-m/mortality-from-prostate-cancer-number-by-age-group-annual-m",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-procedures/hospital-procedures-lower-limb-amputations-in-diabetic-patients-indirectly-standardised-rate-all-ages-annual-trend-f/hospital-procedures-lower-limb-amputations-in-diabetic-patients-indirectly-standardised-rate-all-ages-annual-trend-f",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/infant-mortality/stillbirths-crude-rate-annual-p/stillbirths-crude-rate-annual-p",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-stroke-indirectly-standardised-percent-all-ages-annual-trend-m/emergency-readmissions-to-hospital-within-28-days-of-discharge-stroke-indirectly-standardised-percent-all-ages-annual-trend-m",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-primary-hip-replacement-surgery-indirectly-standardised-percent-all-ages-annual-trend-p/emergency-readmissions-to-hospital-within-28-days-of-discharge-primary-hip-replacement-surgery-indirectly-standardised-percent-all-ages-annual-trend-p",
		"/data-and-information/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current/ensuring-people-have-a-positive-experience-of-care-and-support/3a-overall-satisfaction-of-people-who-use-services-with-their-care-and-support/3a-overall-satisfaction-of-people-who-use-services-with-their-care-and-support",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction/mortality-from-acute-myocardial-infarction-directly-standardised-rate-all-ages-3-year-average-mfp/mortality-from-acute-myocardial-infarction-directly-standardised-rate-all-ages-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-malignant-melanoma-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-malignant-melanoma-crude-rate-1-74-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-suicide-or-suicide-and-injury-undetermined/mortality-from-suicide-and-injury-undetermined-directly-standardised-rate-15-years-annual-trend-mfp/mortality-from-suicide-and-injury-undetermined-directly-standardised-rate-15-years-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-stroke-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-stroke-crude-rate-1-74-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-cancers/mortality-from-all-cancers-directly-standardised-rate-65-74-years-annual-trend-mfp/mortality-from-all-cancers-directly-standardised-rate-65-74-years-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-leukaemia/mortality-from-leukaemia-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-leukaemia-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current/safeguarding-people-whose-circumstances-make-them-vulnerable-and-protecting-from-avoidable-harm/4a-proportion-of-people-who-use-services-who-feel-safe/4a-proportion-of-people-who-use-services-who-feel-safe",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/alcohol-consumption/alcohol-consumption-proportion-who-drank-more-than-4-units-and-8-units-males-and-3-units-and-6-units-females-on-any-one-day-last-week-percent-16-years-annual-trend-mf/alcohol-consumption-proportion-who-drank-more-than-4-units-and-8-units-males-and-3-units-and-6-units-females-on-any-one-day-last-week-percent-16-years-annual-trend-mf",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-bronchitis-and-emphysema-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-bronchitis-and-emphysema-crude-rate-1-74-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hodgkin-s-disease/mortality-from-hodgkin-s-disease-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-hodgkin-s-disease-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases/diastolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp/diastolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-causes/mortality-from-all-causes-directly-standardised-rate-65-74-years-annual-trend-mfp/mortality-from-all-causes-directly-standardised-rate-65-74-years-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/limiting-longstanding-illness-standardised-percent-16-years-annual-trend-mfp/limiting-longstanding-illness-standardised-percent-16-years-annual-trend-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-leukaemia-directly-standardised-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-leukaemia-directly-standardised-rate-1-74-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-rheumatic-heart-disease/mortality-from-chronic-rheumatic-heart-disease-indirectly-standardised-ratio-smr-5-44-years-3-year-average-mfp/mortality-from-chronic-rheumatic-heart-disease-indirectly-standardised-ratio-smr-5-44-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidents/mortality-from-accidents-indirectly-standardised-ratio-smr-85-years-3-year-average-mfp/mortality-from-accidents-indirectly-standardised-ratio-smr-85-years-3-year-average-mfp",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-indirectly-standardised-percent-16-74-years-annual-trend-f/emergency-readmissions-to-hospital-within-28-days-of-discharge-indirectly-standardised-percent-16-74-years-annual-trend-f",
		"/data-and-information/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-directly-standardised-rate-all-ages-3-year-average-m/mortality-from-prostate-cancer-directly-standardised-rate-all-ages-3-year-average-m"
	)

	var legacypublications = List(
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2006",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2007",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2008",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2009",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2010",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2011",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2012",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2013",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2014",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2014-additional-tables",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2015",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2016",
		"/data-and-information/publications/statistical/statistics-on-alcohol/statistics-on-alcohol-england-2017",
		"/data-and-information/publications/statistical/nhs-hospital-and-community-health-services-medical-and-dental-staff/nhs-hospital-and-community-health-service-hchs-workforce-statistics-in-england-medical-and-dental-staff-2002-2012-as-at-30-september",
		"/data-and-information/publications/statistical/nhs-hospital-and-community-health-services-medical-and-dental-staff/nhs-hospital-and-community-health-service-hchs-workforce-statistics-in-england-non-medical-staff-2002-2012-as-at-30-september",
		"/data-and-information/publications/statistical/sexual-and-reproductive-health-services/sexual-and-reproductive-health-services-england-2014-15",
		"/data-and-information/publications/statistical/the-quality-of-nationally-submitted-health-and-social-care-data/the-quality-of-nationally-submitted-health-and-social-care-data-england-2013-second-annual-report-experimental-statistics",
		"/data-and-information/publications/statistical/national-oesophago-gastric-cancer-audit/national-oesophago-gastric-cancer-audit-2014-annual-report",
		"/data-and-information/publications/statistical/national-lung-cancer-audit/national-clinical-audit-support-programme-lung-cancer-nlca-report-2005",
		"/data-and-information/publications/statistical/national-head-and-neck-cancer-audit/national-head-and-neck-cancer-audit-2009-dahno-fifth-annual-report",
		"/data-and-information/publications/statistical/national-head-and-neck-cancer-audit/national-head-and-neck-cancer-audit-dahno-first-annual-report",
		"/data-and-information/publications/statistical/national-bowel-cancer-audit/national-bowel-cancer-audit-trust-level-reports-england-2016-a-to-c",
		"/data-and-information/publications/statistical/national-bowel-cancer-audit/national-bowel-cancer-audit-report-2014",
		"/data-and-information/publications/statistical/health-and-care-of-people-with-learning-disabilities/health-and-care-of-people-with-learning-disabilities-experimental-statistics-2016-to-2017",
	)

	var searches = List(
		"/search?query=alcohol",
		"/search?query=hospital",
		"/search?query=maternity",
		"/search?query=measles",
		"/search/document-type/publication?query=health"
	)

	var executions = Seq(
		exec(http("/").get("/")),
		exec(http("ci-hub ccg-outcomes-indicator-set").get("/data-and-information/publications/ci-hub/ccg-outcomes-indicator-set")),
		exec(http("ci-hub compendium-indicators").get("/data-and-information/publications/ci-hub/compendium-indicators")),
		exec(http("ci-hub social-care").get("/data-and-information/publications/ci-hub/social-care"))
		exec(http("ci-hub nhs-outcomes-framework").get("/data-and-information/publications/ci-hub/nhs-outcomes-framework")),
		exec(http("ci-hub summary-hospital-level-mortality-indicator-shmi").get("/data-and-information/publications/ci-hub/summary-hospital-level-mortality-indicator-shmi")),
		exec(http("ci-hub seven-day-services").get("/data-and-information/publications/ci-hub/seven-day-services"))
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

	legacypublications.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"legacypublications #$i").get(el) )
	} }

	searches.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"search #$i").get(el) )
	} }

	val scn = scenario("PublicationSystemLoadTest").exec(Random.shuffle(executions))
}
