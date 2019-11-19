(function() {
    'use strict';

    angular
        .module('mssjappApp')
        .controller('Tester_setupDialogController', Tester_setupDialogController);

    Tester_setupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tester_setup'];

    function Tester_setupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tester_setup) {
        var vm = this;

        vm.tester_setup = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tester_setup.id !== null) {
                Tester_setup.update(vm.tester_setup, onSaveSuccess, onSaveError);
            } else {
                Tester_setup.save(vm.tester_setup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mssjappApp:tester_setupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
