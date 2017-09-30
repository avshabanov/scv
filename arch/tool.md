
# Sample

```lisp
(world
  (dir "/sample/"
    (file "1.txt")
    -- Note: 2.txt is redundant, one entry is enough to guess an action
    (file "2.txt")))
    
(tool "zipper"
  (input (dir "/sample/"))
  (output
    (dir "/sample/"
      (file "1.txt.zip")
      (file "2.txt.zip"))))
```
