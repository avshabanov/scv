package com.truward.scv.experiments;

import com.truward.scv.experiments.cst.Cst;

public final class GenToolApp {

  public static void main(String[] args) {
    System.out.printf("demo script = %s", demo());
  }

  private static Cst.Script demo() {
    return Cst.Script.create()
        .setWorld(Cst.World.create()
            .add(Cst.Fs.create()
                .addElement(Cst.Dir.create().setName("/sample/dir/")
                    .addElement(Cst.File.create().setName("1.txt"))
                    .addElement(Cst.File.create().setName("2.txt")))));
  }
}
