## 2.0 BOOTSTRAP JS PHILOSOPHY
These are the high-level design rules which guide the development of Bootstrap's JS plugins.

---

### DATA-ATTRIBUTE API

We believe you should be able to use all plugins provided by Bootstrap purely through the markup API without writing a single line of javascript.

We acknowledge that this isn't always the most performant and sometimes it may be desirable to turn this functionality off altogether. Therefore, as of 2.0 we provide the ability to disable the data attribute API by unbinding all events on the body namespaced with `'data-api'`. This looks like this:

    $('body').unbind('.data-api')

To target a specific plugin, just include the plugins name as a namespace along with the data-api namespace like this:

    $('body').unbind('.alert.data-api')

---

### PROGRAMATIC API

We also believe you should be able to use all plugins provided by Bootstrap purely through the JS API.

All public APIs should be a single, chainable method, and return the collection acted upon.

    $(".btn.danger").button("toggle").addClass("fat")

All methods should accept an optional options object, a string which targets a particular method, or null which initiates the default behavior:

    $("#myModal").modal() // initialized with defaults

---

### PLUGIN OPTIONS

Options should be sparse and add universal value. We should pick the right defaults.

All plugins should have a default object which can be modified to effect all instance's default options. The defaults object should be available via `$.fn.plugin.defaults`.

    $.fn.modal.defaults = { … }

An options definition should take the following form:

    *noun*: *adjective* - describes or modifies a quality of an instance

examples:

    backdrop: true
    keyboard: false
    placement: 'above'

---

### PLUGIN EVENTS

All events should have an infinitive and past participle form. The infinitive is fired just before an action takes place, the past participle on completion of the action.

    show | shown
    hide | hidden

---


### DATA ATTRIBUTES

Data attributes should take the following form:

- data-*(verb)* - defines main interaction
- data-target || href^=# - defined on controller element (if element interacts with an element other than self)
- data-*(noun)* - defines options for element invocation

examples:

    // control other targets
    data-toggle="modal" data-target="#foo"
    data-toggle="collapse" data-target="#foo" data-parent="#foo"

    // defined on element they control
    data-spy="scroll"

    data-dismiss="modal"
    data-dismiss="alert"

    data-toggle="dropdown"

    data-toggle="button"
    data-toggle="buttons-checkbox"
    data-toggle="buttons-radio"