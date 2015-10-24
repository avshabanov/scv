package com.truward.scv.cli.processor;

import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import com.truward.scv.cli.mapping.support.DefaultTargetMappingService;
import com.truward.scv.plugin.api.SpecificationProcessorContext;
import com.truward.scv.plugin.api.spi.SpecificationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ServiceLoader;

/**
 * Represents a dependency injection context that loads all the beans.
 */
public class DefaultProcessorContext implements SpecificationProcessorContext {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final InjectionContext injectionContext;

  public DefaultProcessorContext() {
    injectionContext = new DefaultInjectionContext();
  }

  public void addDefaults() {
    // general-purpose beans
    registerBean(SpecificationHandler.class);
    registerBean(DefaultTargetMappingService.class);

    addSpecificationPlugins();

    log.info("SCV good to go sir!");
  }

  public InjectionContext getInjectionContext() {
    return injectionContext;
  }

  @Override
  public void registerBean(@Nonnull Class<?> implementationClass) {
    getInjectionContext().registerBean(implementationClass);
  }

  protected void addSpecificationPlugins() {
    // add plugins using ServiceLoader
    final ServiceLoader<SpecificationPlugin> driverServiceLoader = ServiceLoader.load(SpecificationPlugin.class);
    for (final SpecificationPlugin driver : driverServiceLoader) {
      log.info("Using driver: {}", driver);
      driver.joinTo(this);
    }
  }
}
