package com.truward.scv.contrib.plugin.pojo.api;

import com.truward.scv.contrib.plugin.pojo.api.binding.MultiplePojoTargets;
import com.truward.scv.specification.annotation.Specification;
import com.truward.scv.specification.annotation.TargetMapping;
import com.truward.scv.specification.annotation.TargetMappingEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PojoSpecifierSupport}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class PojoSpecifierSupportTest {
  @Mock PojoSpecifier pojoSpecifierMock;
  @Mock MultiplePojoTargets multiplePojoTargetsMock;

  @Test
  public void shouldCallDelegateMethodsForMultipleClasses() {
    // Given:
    final SampleSpec sampleSpec = new SampleSpec();
    sampleSpec.setPojoSpecifier(pojoSpecifierMock);
    when(pojoSpecifierMock.forClasses(User.class, Manager.class, UserProfile.class)).thenReturn(multiplePojoTargetsMock);

    // When:
    sampleSpec.shouldCreateMultipleDataClasses();

    // Then:
    verify(multiplePojoTargetsMock).makeDataClass();
  }

  //
  // Private
  //

  private interface User {
//    String getName();
//    int getAge();
  }

  private interface Manager {
//    Collection<User> getSubordinates();
  }

  private interface UserProfile {
//    User getUser();
//    boolean isExpired();
  }

  @TargetMapping(@TargetMappingEntry(source = {User.class, Manager.class, UserProfile.class},
      targetClassName = "test.pojo.UserModel"))
  private static final class SampleSpec extends PojoSpecifierSupport {

    @Specification
    public void shouldCreateMultipleDataClasses() {
      final MultiplePojoTargets targets = forClasses(User.class, Manager.class, UserProfile.class);
      targets.makeDataClass();
    }
  }
}
