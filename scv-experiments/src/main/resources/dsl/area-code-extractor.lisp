(version "1")

;;
;; Input definition
;;

(input source :type String)

;;
;; Functions
;;

(function if
          :args ((:type Boolean) (:type String) (:type String)))

(function contains :type Boolean
          :args ((:type String) (:type Regex) (:type Int)))

(function substring :type String
          :args ((:type String) (:type Int)))

;;
;; Rules
;;

(rule @Root :type String
      (if @Predicate @Body @Root)
      @Body)

(rule @Predicate :type Boolean
      (contains source @PredicateRegex @Index))

(rule @Body :type String
      (concat @Atom @Body)
      @Atom)

(rule @Atom
      (const :type String)
      (substring source @Index))

(rule @Index :type Int (var :min-value 0))

(rule @PredicateRegex :type Regex (var))

;;(rule @Pos :type Int (var))
