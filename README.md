# innuendo

This is a little API library for the [RefHeap](http://refheap.com/api) pasting API. It implements the entire API.

## Usage

Check https://clojars.org/innuendo for the latest version.

Here are some examples:

```clojure
user=> (use 'innuendo.core)
nil
user=> (get-paste 21)
{:lines 1, :date "2012-01-05T14:40:25.878Z", :paste-id "21", :language "Clojure", :private false, :url "http://refheap.com/paste/21", :user nil, :contents "test"}
user=> (create-paste "(println \"test\")" {:username "Raynes" :token "REDACTED" :private true :language "Clojure"})
{:lines 1, :date "2012-01-05T20:55:21.440Z", :paste-id "4f060e39535d6a2321a5aec1", :language "Clojure", :private true, :url "http://refheap.com/paste/4f060e39535d6a2321a5aec1", :user "raynes", :contents "(println \"test\")"}
user=> (edit-paste "4f060e39535d6a2321a5aec1" {:username "Raynes" :token "REDACTED" :contents "(println \"edited\")" :private false})
{:lines 1, :date "2012-01-05T20:55:21.440Z", :paste-id "22", :language "Clojure", :private false, :url "http://refheap.com/paste/22", :user "raynes", :contents "(println \"edited\")"}
user=> (create-paste "(println \"unauthenticated\")" {:private true :language "Clojure"})
{:lines 1, :date "2012-01-05T20:57:05.519Z", :paste-id "4f060ea1535d6a2321a5aec2", :language "Clojure", :private true, :url "http://refheap.com/paste/4f060ea1535d6a2321a5aec2", :user nil, :contents "(println \"unauthenticated\")"}
user=> (delete-paste 22 {:username "Raynes" :token "REDACTED"})
true
```

## License

Distributed under the Eclipse Public License, the same as Clojure.
