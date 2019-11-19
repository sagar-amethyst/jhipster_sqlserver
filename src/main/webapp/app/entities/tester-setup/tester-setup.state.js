(function() {
    'use strict';

    angular
        .module('mssjappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tester-setup', {
            parent: 'entity',
            url: '/tester-setup?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tester_setups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tester-setup/tester-setups.html',
                    controller: 'Tester_setupController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('tester-setup-detail', {
            parent: 'tester-setup',
            url: '/tester-setup/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tester_setup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tester-setup/tester-setup-detail.html',
                    controller: 'Tester_setupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tester_setup', function($stateParams, Tester_setup) {
                    return Tester_setup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tester-setup',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tester-setup-detail.edit', {
            parent: 'tester-setup-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tester-setup/tester-setup-dialog.html',
                    controller: 'Tester_setupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tester_setup', function(Tester_setup) {
                            return Tester_setup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tester-setup.new', {
            parent: 'tester-setup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tester-setup/tester-setup-dialog.html',
                    controller: 'Tester_setupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                test_name: null,
                                product: null,
                                quater: null,
                                week: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tester-setup', null, { reload: 'tester-setup' });
                }, function() {
                    $state.go('tester-setup');
                });
            }]
        })
        .state('tester-setup.edit', {
            parent: 'tester-setup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tester-setup/tester-setup-dialog.html',
                    controller: 'Tester_setupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tester_setup', function(Tester_setup) {
                            return Tester_setup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tester-setup', null, { reload: 'tester-setup' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tester-setup.delete', {
            parent: 'tester-setup',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tester-setup/tester-setup-delete-dialog.html',
                    controller: 'Tester_setupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tester_setup', function(Tester_setup) {
                            return Tester_setup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tester-setup', null, { reload: 'tester-setup' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
