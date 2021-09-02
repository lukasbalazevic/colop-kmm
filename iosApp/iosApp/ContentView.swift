import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View {
    @ObservedObject var viewModel: CoinsViewModel = CoinsViewModel()

    var body: some View {
        VStack {


            Button("Do Magic") {
                viewModel.getCoins()
            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//        ContentView()
//	}
//}
