(function() {
    'use strict';

    angular
        .module('mssjappApp')
        .controller('Tester_setupDeleteController',Tester_setupDeleteController);

    Tester_setupDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tester_setup'];

    function Tester_setupDeleteController($uibModalInstance, entity, Tester_setup) {
        var vm = this;

        vm.tester_setup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tester_setup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
