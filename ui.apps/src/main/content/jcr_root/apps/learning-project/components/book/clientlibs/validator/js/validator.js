(function ($, Coral) {
    "use strict";
    var registry = $(window).adaptTo("foundation-registry");
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=learning-project-multifield]",
        validate: function (element) {
            var el = $(element);
            let max = el.data("max-items");
            let min = el.data("min-items");
            let items = el.children("coral-multifield-item").length;
            console.log("{} : {} : {}", max, min, items);
            if (items > max) {
                return "You can add only " + max + " books. But you are trying to add " + items + "th book."
            }
            if (items < min) {
                return "You add " + items + " books."
            }
        }
    });

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=learning-project-firstname]",
        validate: function (element) {
            var el = $(element);
            let pattern = /[0-9a-z]/;
            let value = el.val();
            if (pattern.test(value)) {
                return "Please add only Upper case Letters in First name";
            }
        }
    });

})(jQuery, Coral);
