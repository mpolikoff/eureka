package com.netflix.eureka2.config;

import com.netflix.eureka2.registry.eviction.EvictionStrategyProvider;
import com.netflix.eureka2.registry.eviction.EvictionStrategyProvider.StrategyType;

import static com.netflix.eureka2.config.ConfigurationNames.RegistryNames.evictionAllowedPercentageDropName;
import static com.netflix.eureka2.config.ConfigurationNames.RegistryNames.evictionStrategyTypeName;
import static com.netflix.eureka2.config.ConfigurationNames.RegistryNames.evictionStrategyValueName;
import static com.netflix.eureka2.config.ConfigurationNames.RegistryNames.evictionTimeoutMsName;

/**
 * basic eureka registry config that reads properties from System.properties if available,
 * but also allows programmatic overrides and provides some defaults.
 * @author David Liu
 */
public class BasicEurekaRegistryConfig implements EurekaRegistryConfig {

    public static final long EVICTION_TIMEOUT_MS = 30000;
    public static final StrategyType EVICTION_STRATEGY_TYPE = StrategyType.PercentageDrop;
    public static final String EVICTION_STRATEGY_VALUE = "20";
    public static final int EVICTION_ALLOWED_PERCENTAGE_DROP = 20;

    private final Long evictionTimeoutMs;
    private final StrategyType evictionStrategyType;
    private final String evictionStrategyValue;
    private final int evictionAllowedPercentageDrop;

    private BasicEurekaRegistryConfig(Long evictionTimeoutMs, StrategyType evictionStrategyType,
                                      String evictionStrategyValue, int evictionAllowedPercentageDrop) {
        this.evictionTimeoutMs = evictionTimeoutMs;
        this.evictionStrategyType = evictionStrategyType;
        this.evictionStrategyValue = evictionStrategyValue;
        this.evictionAllowedPercentageDrop = evictionAllowedPercentageDrop;
    }

    @Override
    public long getEvictionTimeoutMs() {
        return evictionTimeoutMs;
    }

    @Override
    public StrategyType getEvictionStrategyType() {
        return evictionStrategyType;
    }

    @Override
    public String getEvictionStrategyValue() {
        return evictionStrategyValue;
    }

    @Override
    public int getEvictionAllowedPercentageDrop() {
        return evictionAllowedPercentageDrop;
    }

    @Override
    public String toString() {
        return "BasicEurekaRegistryConfig{" +
                "evictionTimeoutMs=" + evictionTimeoutMs +
                ", evictionStrategyType=" + evictionStrategyType +
                ", evictionStrategyValue='" + evictionStrategyValue + '\'' +
                ", evictionAllowedPercentageDrop=" + evictionAllowedPercentageDrop +
                '}';
    }

    public static class Builder {
        private Long evictionTimeoutMs = SystemConfigLoader.
                getFromSystemPropertySafe(evictionTimeoutMsName, EVICTION_TIMEOUT_MS);
        private EvictionStrategyProvider.StrategyType evictionStrategyType = SystemConfigLoader.
                getFromSystemPropertySafe(evictionStrategyTypeName, EVICTION_STRATEGY_TYPE);
        private String evictionStrategyValue = SystemConfigLoader.
                getFromSystemPropertySafe(evictionStrategyValueName, EVICTION_STRATEGY_VALUE);
        private int evictionAllowedPercentageDrop = SystemConfigLoader.
                getFromSystemPropertySafe(evictionAllowedPercentageDropName, EVICTION_ALLOWED_PERCENTAGE_DROP);

        public Builder withEvictionTimeoutMs(Long evictionTimeoutMs) {
            this.evictionTimeoutMs = evictionTimeoutMs;
            return this;
        }

        public Builder withEvictionStrategyType(EvictionStrategyProvider.StrategyType evictionStrategyType) {
            this.evictionStrategyType = evictionStrategyType;
            return this;
        }

        public Builder withEvictionStrategyValue(String evictionStrategyValue) {
            this.evictionStrategyValue = evictionStrategyValue;
            return this;
        }

        public Builder withEvictionAllowedPercentageDrop(int evictionAllowedPercentageDrop) {
            this.evictionAllowedPercentageDrop = evictionAllowedPercentageDrop;
            return this;
        }

        public BasicEurekaRegistryConfig build() {
            return new BasicEurekaRegistryConfig(evictionTimeoutMs, evictionStrategyType,
                    evictionStrategyValue, evictionAllowedPercentageDrop);
        }
    }
}