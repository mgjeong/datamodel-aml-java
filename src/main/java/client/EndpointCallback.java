package client;

/**
 * represents the Callback interface to set Endpoint on APP 
 *    
 */

public interface EndpointCallback {
	
	
	/**
	 * @fn void setStreamEndpint(String endpoint)
	 * @brief Set Endpoint from subscribe message 
	 */
	void setStreamEndpoint(String endpoint);
	
	/**
	 * @fn void setBatchEndpint(String endpoint)
	 * @brief Set Endpoint from subscribe message 
	 */
	void setBatchEndpoint(String endpoint);
	
	/**
	 * @fn void setFileEndpint(String endpoint)
	 * @brief Set Endpoint from subscribe message 
	 */
	void setFileEndpoint(String endpoint);
}
