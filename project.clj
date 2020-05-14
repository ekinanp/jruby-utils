(defproject puppetlabs/jruby-utils "0.12.2-SNAPSHOT"
  :description "A library for working with JRuby"
  :url "https://github.com/puppetlabs/jruby-utils"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}

  :min-lein-version "2.7.1"
  :parent-project {:coords [puppetlabs/clj-parent "0.4.3"]
                   :inherit [:managed-dependencies]}

  :pedantic? :abort

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :test-paths ["test/unit" "test/integration"]

  :dependencies [[org.clojure/clojure]
                 [org.clojure/tools.logging]

                 [me.raynes/fs]
                 [prismatic/schema]
                 [slingshot]

                 [puppetlabs/jruby-deps "1.7.27-1"]

                 [puppetlabs/i18n]
                 [puppetlabs/kitchensink]
                 [puppetlabs/trapperkeeper]
                 [puppetlabs/ring-middleware]]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/clojars_jenkins_username
                                     :password :env/clojars_jenkins_password
                                     :sign-releases false}]]

  ;; By declaring a classifier here and a corresponding profile below we'll get an additional jar
  ;; during `lein jar` that has all the code in the test/ directory. Downstream projects can then
  ;; depend on this test jar using a :classifier in their :dependencies to reuse the test utility
  ;; code that we have.
  :classifiers [["test" :testutils]]

  :profiles {:dev {:dependencies  [[puppetlabs/kitchensink :classifier "test" :scope "test"]
                                   [puppetlabs/trapperkeeper :classifier "test" :scope "test"]
                                   [org.tcrawley/dynapath]]
                   :jvm-opts ["-Djruby.logger.class=com.puppetlabs.jruby_utils.jruby.Slf4jLogger"
                              "-Xms1G"
                              "-Xmx2G"]}
             :testutils {:source-paths ^:replace ["test/unit" "test/integration"]}
             :jruby9k {:dependencies [[puppetlabs/jruby-deps "9.1.15.0-2"]]}}

  :plugins [[lein-parent "0.3.1"]
            [puppetlabs/i18n "0.7.1"]])
