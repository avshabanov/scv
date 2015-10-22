package com.truward.scv.cli.processor;

import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import com.truward.scv.specification.annotation.Specification;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link SpecificationHandler}.
 */
public final class SpecificationHandlerTest {
  private SpecificationHandler specificationHandler;


  @Before
  public void init() {
    final InjectionContext context = new DefaultInjectionContext();
    context.registerBean(SpecificationHandler.class);
    specificationHandler = context.getBean(SpecificationHandler.class);
  }

  @Test
  public void shouldProcessNullSpec() {
    assertNotNull(specificationHandler.parseClass(TestSpec.class));
    specificationHandler.done();
  }

  @Test
  public void shouldSortSpecByPrioritites() {
    // spec 1
    OrdinalSumProvider sumProvider = specificationHandler.parseClass(OrderedSpec1.class);
    assertNotNull(sumProvider);
    assertEquals("Accumulated ordinal sum should be 123", 123, sumProvider.getOrdinalOrderSum());

    // spec 2
    sumProvider = specificationHandler.parseClass(OrderedSpec2.class);
    assertNotNull(sumProvider);
    assertEquals("Accumulated ordinal sum should be 123", 123, sumProvider.getOrdinalOrderSum());
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
}
