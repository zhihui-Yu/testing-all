package com.example.config;

import com.example.Flags;
import io.rollout.client.ConfigurationFetchedHandler;
import io.rollout.client.FetcherResults;
import io.rollout.client.FetcherStatus;
import io.rollout.rox.server.Rox;
import io.rollout.rox.server.RoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

/**
 * @author simple
 */
@Configuration
public class FlagConfig {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Initialize container class that we created earlier
        Flags flags = new Flags();
        Thread thread = new Thread(() -> {
            try {
                // Register the flags container with Rollout
                Rox.register(flags);

                // Building options
                RoxOptions options = new RoxOptions.Builder().withConfigurationFetchedHandler(new ConfigurationFetchedHandler() {
                    @Override
                    public void onConfigurationFetched(FetcherResults fetcherResults) {
                        if (fetcherResults != null) {
                            FetcherStatus status = fetcherResults.getFetcherStatus();
                            // configuration loaded from network, flags value updated
                            if (status != null && status == FetcherStatus.AppliedFromNetwork) {
                                System.out.println("Thread: " + Thread.currentThread().getName() + ":" + ZonedDateTime.now() + " -> updated now.");
                            }
                        }
                    }
                }).build();

                /**
                 * 内部有个子线程去执行获取和更新操作。
                 */
                Rox.setup("624d4dc812463094933f2593", options).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("thread end  =============");
        });
        thread.start();
        // Setup the Rollout environment key
        System.out.println("waiting =============");

        thread.join();

        System.out.println("after =============");
        // Boolean flag example
        if (flags.enableTutorial.isEnabled()) {
            // TODO:  Put your code here that needs to be gated
            System.out.println("enable Tutorial.");
        }

        // String flag example
        String titleColor = flags.titleColors.getValue();
        System.out.printf("Title color is %s ", titleColor);

        // Integer flag example
        int titleSize = flags.titleSize.getValue();
        System.out.printf("Title size is %d ", titleSize);

        // Double flag example
        double specialNumber = flags.specialNumber.getValue();
        System.out.printf("Special number is %f ", specialNumber);

        // Enum flag example
        Flags.Color color = flags.titleColorsEnum.getValue();
        System.out.printf("Enum color is %s ", color.name());

    }


    /**
     * CloudBees 上 字段需要开启 targeting才能有效果。
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Bean
    public Flags flag() throws ExecutionException, InterruptedException {
        // Initialize container class that we created earlier
        Flags flags = new Flags();

        // Register the flags container with Rollout
        Rox.register(flags);

        // Building options
        RoxOptions options = new RoxOptions.Builder().withConfigurationFetchedHandler(new ConfigurationFetchedHandler() {
            @Override
            public void onConfigurationFetched(FetcherResults fetcherResults) {
                if (fetcherResults != null) {
                    FetcherStatus status = fetcherResults.getFetcherStatus();
                    // configuration loaded from network, flags value updated
                    if (status != null && status == FetcherStatus.AppliedFromNetwork) {
                        System.out.println("Thread: " + Thread.currentThread().getName() + ":" + ZonedDateTime.now() + " -> updated now.");
                    }
                }
            }
        }).build();
        // Setup the Rollout environment key
        Rox.setup("624d4dc812463094933f2593", options).get();

        // Boolean flag example
        if (flags.enableTutorial.isEnabled()) {
            // TODO:  Put your code here that needs to be gated
            System.out.println("enable Tutorial.");
        }

        // String flag example
        String titleColor = flags.titleColors.getValue();
        System.out.printf("Title color is %s ", titleColor);

        // Integer flag example
        int titleSize = flags.titleSize.getValue();
        System.out.printf("Title size is %d ", titleSize);

        // Double flag example
        double specialNumber = flags.specialNumber.getValue();
        System.out.printf("Special number is %f ", specialNumber);

        // Enum flag example
        Flags.Color color = flags.titleColorsEnum.getValue();
        System.out.printf("Enum color is %s ", color.name());

        return flags;
    }
}
