# Loan Management
此專案是一個名為Loan Management的Android應用程式，主要用於管理和追蹤貸款記錄。使用 MVVM (Model-View-ViewModel) 設計模式，並採用了其他Android開發技術，如DataBinding、Room Database、LiveData、Fragment、RecycleView等。

以下是此專案的主要功能：
* 新增貸款記錄：使用者可以新增貸款記錄，包括貸款人名稱、金額、日期、是否已付款以及備註。
* 編輯貸款記錄：使用者可以編輯現有的貸款記錄。
* 搜尋貸款記錄：使用者可以根據多種條件（如貸款人名稱、金額範圍、日期範圍、是否已付款以及關鍵字）來搜尋貸款記錄。
* 刪除貸款記錄：使用者可以刪除特定的貸款記錄。

## 專案架構:
```
|--app
|  |--model                            #包含此專案的所有數據模型
|  |  |--LoanDao.kt
|  |  |--LoanDatabase.kt
|  |  |--LoanEntity.kt
|  |--repository                       #包含此專案的數據庫存取邏輯
|  |  |--LoanRepository.kt
|  |--view                             #包含此專案所有的UI元件
|  |  |--MainActivity.kt
|  |  |--adapter                       #包含所有UI元件的adapter
|  |  |  |--BindingAdapter.kt
|  |  |  |--LoanRecycleViewAdapter.kt
|  |  |--fragment                      #包含所有的fragment
|  |  |  |--AddFragment.kt
|  |  |  |--EditFragment.kt
|  |  |  |--MainFragment.kt
|  |  |  |--SearchFragment.kt
|  |--viewmodel                        #包含此專案的ViewModel
|  |  |---LoanViewModel.kt
```
