//
//  CoinsViewModel.swift
//  iosApp
//
//  Created by RD on 28.07.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

@available(iOS 14.0, *)
class CoinsViewModel : BaseViewModel, ObservableObject {
    @Published var coins = [Coin]()

    private let getCoinsUseCase = GetCoinsListUseCase()

    override init() {
        super.init()
        self.getCoins()
    }

    func getCoins() {
        getCoinsUseCase.execute(self, arg: KotlinUnit()) {
            $0.onSuccess { list in
               print(list)
            }
        }
    }
}
