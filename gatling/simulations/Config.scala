object Config {
	// pass via command line i.e. JAVA_OPTS="-DbaseUrl=https://www.nhs.hosting.onehippo.com"
	val host: String = if (System.getProperty("baseUrl") != null) {
		System.getProperty("baseUrl")
	} else {
		// default address
		"http://localhost:8080/site"
	}
}
