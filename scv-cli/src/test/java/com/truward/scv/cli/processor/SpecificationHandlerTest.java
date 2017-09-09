package com.truward.scv.cli.processor;

import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import com.truward.scv.cli.mapping.MappedClass;
import com.truward.scv.cli.mapping.MappedTarget;
import com.truward.scv.cli.mapping.TargetMappingProvider;
import com.truward.scv.cli.mapping.support.DefaultTargetMappingService;
import com.truward.scv.specification.annotation.Specification;
import com.truward.scv.specification.annotation.TargetMapping;
import com.truward.scv.specification.annotation.TargetMappingEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SpecificationHandler}.
 */
public final class SpecificationHandlerTest {

  private SpecificationHandler specificationHandler;
  private TargetMappingProvider mappingProvider;

  @Before
  public void init() {
    final InjectionContext context = new DefaultInjectionContext();
    context.registerBean(SpecificationHandler.class);
    context.registerBean(DefaultTargetMappingService.class);
    specificationHandler = context.getBean(SpecificationHandler.class);
    mappingProvider = context.getBean(TargetMappingProvider.class);
  }

  @Test
  public void shouldProcessNullSpec() {
    assertNotNull(specificationHandler.parseClass(TestSpec.class));
    specificationHandler.done();
  }

  @Test
  public void shouldSortSpecByPriorities() {
    // spec 1
    OrdinalSumProvider sumProvider = specificationHandler.parseClass(OrderedSpec1.class);
    assertNotNull(sumProvider);
    assertEquals("Accumulated ordinal sum should be 123", 123, sumProvider.getOrdinalOrderSum());

    // spec 2
    sumProvider = specificationHandler.parseClass(OrderedSpec2.class);
    assertNotNull(sumProvider);
    assertEquals("Accumulated ordinal sum should be 123", 123, sumProvider.getOrdinalOrderSum());
  }

  @Test
  public void shouldProcessNoTargetEntries() {
    final NoMappingSpec spec = specificationHandler.parseClass(NoMappingSpec.class);
    assertNotNull(spec);

    final List<MappedTarget> mappedTargets = mappingProvider.getMappedTargets();
    assertTrue(mappedTargets.isEmpty());
  }

  @Test
  public void shouldProcessTargetEntries() {
    final TargetMappingSpec spec = specificationHandler.parseClass(TargetMappingSpec.class);
    assertNotNull(spec);

    final List<MappedTarget> mappedTargets = mappingProvider.getMappedTargets();
    assertEquals(1, mappedTargets.size());

    final MappedTarget mappedTarget = mappedTargets.get(0);
    final MappedClass mappedClass = mappedTarget.getPrimaryMappedClass();
    assertNotNull(mappedClass);
    assertEquals(Serializable.class, mappedClass.getSourceClass());
    assertTrue(mappedClass.getTargetName().isEmpty());
    assertEquals(mappedTarget, mappedClass.getMappedTarget());
  }

  //
  // Test data, should be public
  //

  public static final class TestSpec {

    @Specification
    public void nullSpec() {
    }
  }

  // ordinals test

  public static abstract class OrdinalSumProvider {
    private int multiplier = 1;
    private int sum = 0;

    public final int getOrdinalOrderSum() {
      return sum;
    }

    protected void inc(int pos) {
      sum += multiplier * pos;
      multiplier *= 10;
    }
  }
  public static final class OrderedSpec1 extends OrdinalSumProvider {
    @Specification(priority = 1)
    public void spec1() {
      inc(1);
    }

    @Specification(priority = 2)
    public void spec2() {
      inc(2);
    }

    @Specification
    public void lastSpec() {
      inc(3);
    }
  }

  // changed order of appearance
  public static final class OrderedSpec2 extends OrdinalSumProvider {
    @Specification(priority = 2)
    public void spec2() {
      inc(2);
    }

    @Specification
    public void lastSpec() {
      inc(3);
    }

    @Specification(priority = 1)
    public void spec1() {
      inc(1);
    }
  }

  public static final class NoMappingSpec {
    @Specification
    public void spec() {}
  }

  @TargetMapping({
      @TargetMappingEntry(source = Serializable.class, targetName = "my.generated.SerializableImpl")
  })
  public static final class TargetMappingSpec {
    @Specification
    public void spec() {}
  }

  @TargetMapping({
      @TargetMappingEntry(source = Serializable.class, targetPackageName = "my.generated")
  })
  public static final class TargetMappingSpecWithPackageNameOutput {
    @Specification
    public void spec() {}
  }

  @TargetMapping({
      @TargetMappingEntry(source = Serializable.class, targetPackageClass = SpecificationHandlerTest.class)
  })
  public static final class TargetMappingSpecWithPackageClassOutput {
    @Specification
    public void spec() {}
  }
}
