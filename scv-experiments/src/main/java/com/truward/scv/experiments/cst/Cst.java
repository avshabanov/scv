package com.truward.scv.experiments.cst;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public final class Cst {
  private Cst() {}

  //
  // Entities
  //

  public static final class Script {
    private World world;

    private Script() {}

    public static Script create() {
      return new Script();
    }

    public Script setWorld(World world) {
      this.world = world;
      return this;
    }
  }

  public interface WorldElement {
  }

  public static final class World {
    private final List<WorldElement> elements = new ArrayList<>();

    private World() {}

    public static World create() {
      return new World();
    }

    public World add(WorldElement element) {
      this.elements.add(element);
      return this;
    }
  }

  public interface FsElement {
    String getName();
  }

  public interface FsContainer {
    List<FsElement> getElements();
  }

  public static final class Fs extends FsContainerBase<Fs> implements WorldElement {
    private Fs() {}

    public static Fs create() {
      return new Fs();
    }
  }

  public interface Self<T> {
    default T self() {
      @SuppressWarnings("unchecked") T result = (T) this;
      return result;
    }
  }

  public static abstract class Named<T> implements Self<T> {
    String name = "";

    public T setName(String name) {
      this.name = name;
      return self();
    }

    public String getName() {
      return name;
    }
  }

  public static abstract class FsContainerBase<T> extends Named<T> implements FsContainer {
    private final List<FsElement> elements = new ArrayList<>();

    public T addElement(FsElement element) {
      this.elements.add(element);
      return self();
    }

    @Override
    public List<FsElement> getElements() {
      return this.elements;
    }
  }

  public static final class Dir extends FsContainerBase<Dir> implements FsElement {
    private final List<FsElement> elements = new ArrayList<>();

    private Dir() {}

    public static Dir create() {
      return new Dir();
    }
  }

  public static final class File extends Named<File> implements FsElement {
    private File() {}

    public static File create() {
      return new File();
    }
  }
}
