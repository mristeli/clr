(defproject clr "0.1.0-SNAPSHOT"
  :description "Clojure linear regression API"
  :url "https://github.com/mristeli/clr"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"], 
    [org.clojure/test.check "1.1.0"],
    [org.clojure/math.numeric-tower "0.0.4"]
    ; Compojure - A basic routing library
    [compojure "1.6.1"]
    ; Our Http library for client/server
    [http-kit "2.3.0"]
    ; Ring defaults - for query params etc
    [ring/ring-defaults "0.3.2"]
    ; Parse JSON requests
    [ring/ring-json "0.5.0"]
    ; Clojure data.JSON library
    [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot clr.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
