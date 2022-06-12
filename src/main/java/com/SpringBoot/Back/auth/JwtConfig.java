package com.SpringBoot.Back.auth;

public class JwtConfig {
    
    public static final String LLAVE_SECRETA = "alguna.clave.secreta.1234566789";

	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEA0rvov96rFRbGyXLkLh35DDrhDGLyUjP7eo1TNyzJ6A49Iy1Y\r\n" + 
			"LZZyTFi3pMllaEMCTq2g6Pxcqr8ytrjk3x7LRASUQR1XOw4pQVX9hp+uR2YkLBa3\r\n" + 
			"/GS0ifgSVgtdlQoOvDGFG8bhl+fp9YnHzq3dmp/wmUWLlc+wqRk/OyN/NFGvoVFy\r\n" + 
			"YfbOmeVKrA0veAd7l3GRlCWcRO2IpeaGNxGc4TfDsUoPqui219QkPAyWePY6Ngya\r\n" + 
			"u4M7AugERCvGME607Z65riEuDvK3dUVTk0pxsJB9TsfHmKGBLKcXTdMN2x7g+ByD\r\n" + 
			"z0/6de7yyrmUw6yukjTAVax/IMLiwZbucUmeAwIDAQABAoIBADDc2U8D9m9hZJ55\r\n" + 
			"QWdgE8s+K8iPeNiOrRTxiAYXIInqI0rwzK1lYQNK3uoauKTARNyxbcoYGfSNeNjd\r\n" + 
			"blQ986vF1Ap1HzDWS8KWib6Zb/OmP2Tpr6eaQjJrjvTclcplFp2UbeTO7KgoYhtI\r\n" + 
			"40eNEQE1bLhdY8xM6A2NiRfRV0Ft86+wdM5CDICRZnuN6NtP0b17xKYMcB3uS18r\r\n" + 
			"Q5cQ0K/+lcZ3MPi4Afjc4PsgM9m/TfhAacmNfR8q9qrlx5YJEti12qXqTxjRkF5e\r\n" + 
			"4/ulgPbMRCkA+gk4hUdqu7K55P/vMQSh/bq06relLs4TloGu0Bmtfcd//6v5dUBf\r\n" + 
			"/rqxCsECgYEA6nYy58XTmP/5clLFoxWiEZjmNCCjZH9IeSaZlLF0SFJiHJDX+neP\r\n" + 
			"ehUoyT5cg2Qfqe0/EV2JBqPSc4aXu2Gyb7hogDwl/FTnUENf756VlvCHPKfsbZeo\r\n" + 
			"bm6aeBXLH1zY3E25zyZmP/ltw781XLd92KgzNPirneZ3C4HoT/lsk2sCgYEA5he1\r\n" + 
			"riTiIp8lNOvWd60dsowY6E2vzUMTwQj+UyAOiw9I67VJDdtkpcz/sRxC29v6fDX2\r\n" + 
			"RVdivipAht7tSXq0HDT/c0Q1vFrRGBWsehVL20i97rBW0bZEGWonQR+LAJbdwXwK\r\n" + 
			"Y9ogsQr17DXcr+f5b4vXPCr5E7NNgTuvcVd0XckCgYEAoRm8WqMEhIGF/bbAFBHe\r\n" + 
			"PWco7jJq8re+rXgHWTMhajwpK+WecXxLeqAlWTnfSWy3kQPyLNVvPL+98CFQCrHT\r\n" + 
			"cI5uR4aMtzgDhJP2zGfT81WsJtJfUuI9GjwnKVIYMqGSZLWHcb25zkVt1W2BlgaC\r\n" + 
			"kybnoIMpMKLVs3N8k88XeqcCgYEArfJUti9KCdR4Y1a7j0P/xIBnQA+jDTFdmLx8\r\n" + 
			"0EjW1cgbX55weRpYL4QX9UQhQ2c9KXP+FxPmIU998rhLeRZbXUuvput1GE0r7+JK\r\n" + 
			"vwykiUWn2O/U4rvBJdl7jZ8UDcEAKorkq/I2bsGTgxyBFzbu+qYkdGckYAFYYY/G\r\n" + 
			"pY/w+AkCgYBl0CVIm9lby3dBqnAOjQ3vmC/E3xN4uU5g74NCQeQV/wvDSLnRm+2G\r\n" + 
			"0MDMwKPhMQ8I0mgGjNM4AjxEAaUmm4oQ1AAlUzg6YWr97n1PTEqYJiRUY2qHWZRy\r\n" + 
			"5fnlaTZxSBuW3Q8CDp4aK6b1DM6btHLMkcU1SHZ3x5W0tt9jEY0ykw==\r\n" + 
			"-----END RSA PRIVATE KEY-----";

	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0rvov96rFRbGyXLkLh35\r\n" + 
			"DDrhDGLyUjP7eo1TNyzJ6A49Iy1YLZZyTFi3pMllaEMCTq2g6Pxcqr8ytrjk3x7L\r\n" + 
			"RASUQR1XOw4pQVX9hp+uR2YkLBa3/GS0ifgSVgtdlQoOvDGFG8bhl+fp9YnHzq3d\r\n" + 
			"mp/wmUWLlc+wqRk/OyN/NFGvoVFyYfbOmeVKrA0veAd7l3GRlCWcRO2IpeaGNxGc\r\n" + 
			"4TfDsUoPqui219QkPAyWePY6Ngyau4M7AugERCvGME607Z65riEuDvK3dUVTk0px\r\n" + 
			"sJB9TsfHmKGBLKcXTdMN2x7g+ByDz0/6de7yyrmUw6yukjTAVax/IMLiwZbucUme\r\n" + 
			"AwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}
