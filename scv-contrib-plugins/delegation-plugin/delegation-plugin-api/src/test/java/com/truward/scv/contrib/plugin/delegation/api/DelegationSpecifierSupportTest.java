package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.DelegationTarget;
import com.truward.scv.specification.Specification;
import com.truward.scv.specification.Target;
import com.truward.scv.specification.filter.ClassMethodFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DelegationSpecifierSupport}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class DelegationSpecifierSupportTest {

  @Mock DelegationSpecifier delegationSpecifierMock;
  @Mock DelegationTarget<Foo> fooDelegationTargetMock;
  @Mock ClassMethodFilter<Foo> fooClassMethodFilterMock;
  @Mock Foo fooMock;

  @Test
  public void shouldCallDelegateMethods() {
    // Given:
    final SampleSpec sampleSpec = new SampleSpec();
    sampleSpec.setDelegationSpecifier(delegationSpecifierMock);

    when(delegationSpecifierMock.create(SampleSpec.FOO_TARGET, Foo.class)).thenReturn(fooDelegationTargetMock);
    when(fooDelegationTargetMock.delegateCall()).thenReturn(fooClassMethodFilterMock);
    when(fooClassMethodFilterMock.forMethod()).thenReturn(fooMock);

    // When:
    sampleSpec.shouldCallCreate();

    // Then:
    verify(fooMock).foo();
  }

  //
  // Private
  //

  private interface Foo {
    void foo();
  }

  private static final class SampleSpec extends DelegationSpecifierSupport {
    static final Target FOO_TARGET = Target.fromClassName("test.delegation.generated.CreateTest");

    @Specification
    public void shouldCallCreate() {
      final DelegationTarget<Foo> fooTarget = create(FOO_TARGET, Foo.class);
      fooTarget.delegateCall().forMethod().foo();
    }
  }
}
