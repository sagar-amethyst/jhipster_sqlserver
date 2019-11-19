(function() {
    'use strict';

    angular
        .module('mssjappApp')
        .controller('Tester_setupDetailController', Tester_setupDetailController);

    Tester_setupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tester_setup'];

    function Tester_setupDetailController($scope, $rootScope, $stateParams, previousState, entity, Tester_setup) {
        var vm = this;

        vm.tester_setup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mssjappApp:tester_setupUpdate', function(event, result) {
            vm.tester_setup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
