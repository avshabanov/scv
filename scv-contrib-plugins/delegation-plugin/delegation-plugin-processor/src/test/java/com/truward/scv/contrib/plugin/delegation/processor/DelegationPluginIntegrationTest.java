package com.truward.scv.contrib.plugin.delegation.processor;

import com.truward.scv.cli.processor.DefaultProcessorContext;
import com.truward.scv.cli.processor.SpecificationHandler;
import com.truward.scv.contrib.plugin.delegation.api.DelegationSpecifierSupport;
import com.truward.scv.contrib.plugin.delegation.processor.spi.DelegationPlugin;
import com.truward.scv.specification.annotation.Specification;
import com.truward.scv.specification.annotation.TargetMapping;
import com.truward.scv.specification.annotation.TargetMappingEntry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public final class DelegationPluginIntegrationTest {

  private SpecificationHandler specificationHandler;

  @Before
  public void init() {
    final ProcessorContext context = new ProcessorContext();
    context.addDefaults();
    specificationHandler = context.getInjectionContext().getBean(SpecificationHandler.class);
  }

  @Test
  public void shouldProcessSampleSpecification() {
    final SampleSpecification sp = specificationHandler.parseClass(SampleSpecification.class);
    specificationHandler.done();

    assertNotNull("Sample specification should not be null", sp);
  }

  //
  // Fixture
  //

  public interface Foo {
    void foo();
  }

  @TargetMapping({
      @TargetMappingEntry(source = Foo.class, targetClassName = "integTest.generated.FooDelegate")
  })
  public static final class SampleSpecification extends DelegationSpecifierSupport {

    @Specification
    public void fooSpec() {
      forClass(Foo.class).delegateCall().forAllMethods();
    }
  }

  //
  // Private
  //

  private static final class ProcessorContext extends DefaultProcessorContext {
    @Override
    protected void addSpecificationPlugins() {
      new DelegationPlugin().joinTo(this);
    }
  }
}