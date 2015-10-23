package com.truward.scv.contrib.plugin.delegation.api;

import com.truward.scv.contrib.plugin.delegation.api.binding.ClassDelegationAction;
import com.truward.scv.specification.annotation.Specification;
import com.truward.scv.specification.annotation.TargetMapping;
import com.truward.scv.specification.annotation.TargetMappingEntry;
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
  @Mock
  ClassDelegationAction<Foo> fooClassDelegationActionMock;
  @Mock ClassMethodFilter<Foo> fooClassMethodFilterMock;
  @Mock Foo fooMock;

  @Test
  public void shouldCallDelegateMethods() {
    // Given:
    final SampleSpec sampleSpec = new SampleSpec();
    sampleSpec.setDelegationSpecifier(delegationSpecifierMock);

    when(delegationSpecifierMock.forClass(Foo.class)).thenReturn(fooClassDelegationActionMock);
    when(fooClassDelegationActionMock.delegateCall()).thenReturn(fooClassMethodFilterMock);
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

  @TargetMapping({
      @TargetMappingEntry(source = Foo.class, targetName = "test.delegation.generated.CreateTest")
  })
  private static final class SampleSpec extends DelegationSpecifierSupport {
    @Specification
    public void shouldCallCreate() {
      final ClassDelegationAction<Foo> fooTarget = forClass(Foo.class);
      fooTarget.delegateCall().forMethod().foo();
    }
  }
}
