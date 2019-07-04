package no.fremtind.insurance.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
public class ClientProperties {

    private final IntegrationLayer integrationLayer = new IntegrationLayer();

    public ClientProperties.IntegrationLayer getIntegrationLayer() {
        return integrationLayer;
    }

    public static class IntegrationLayer {
        private String baseUri;
        private String version;

        public IntegrationLayer() {

        }

        public String getBaseUri() {
            return baseUri;
        }

        public void setBaseUri(String baseUri) {
            this.baseUri = baseUri;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}