(function() {
    'use strict';
    angular
        .module('mssjappApp')
        .factory('Tester_setup', Tester_setup);

    Tester_setup.$inject = ['$resource'];

    function Tester_setup ($resource) {
        var resourceUrl =  'api/tester-setups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
