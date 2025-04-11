import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import CryptoJs from "crypto-js"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private static BASE_URL="http://localhost:5050/api";
private static  ENCRYPTION_KEY="my-encryption-key";
authStatusChanged=new EventEmitter<void>();

  constructor(private httP:HttpClient) { }


  encryptAndSaveToStorage(key:string,value:string):void{
    const encrytedValue=CryptoJs.AES.encrypt(value,ApiService.ENCRYPTION_KEY).toString();
    localStorage.setItem(key,encrytedValue);
  }


  private getFromStorageAndDecrypt(key:string): any{
    try {
      const encryptedValue=localStorage.getItem(key);
      if(!encryptedValue){
        return null;
      }else{
        return CryptoJs.AES.decrypt(encryptedValue,ApiService.ENCRYPTION_KEY).toString(CryptoJs.enc.Utf8);
      }
    } catch (error) {
      return null;
    }
  }

  private clearAuth(){
    localStorage.removeItem("token");
    localStorage.removeItem("role");

  }


  private getHeader():HttpHeaders{
    const token=this.getFromStorageAndDecrypt("token");
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
  }


  /*
Auth * Users API METHODs
  */

registerUser(body:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/auth/register`,body);
}

loginUser(body: any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/auth/login`,body);
} 

getLoggedInUserInfo():Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/users/current`,{
    headers: this.getHeader(),
  });
}


/* Category ENDPOINTS */

createCategory(body:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/categories/add`,body,{
    headers:this.getHeader(),
  })
}

getAllCategory():Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/categories/all`,{
    headers:this.getHeader(),
  })
}

getCategoryById(id:String):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/categories/${id}`,{
    headers:this.getHeader(),
  })
}

updatecategory(id:string,body:any):Observable<any>{
  return this.httP.put(`${ApiService.BASE_URL}/categories/update/${id}`,body,{
    headers:this.getHeader(),
  })
}

deleteCategory(id:string):Observable<any>{
  console.log(id)
  return this.httP.delete(`${ApiService.BASE_URL}/categories/delete/${id}`,{
    headers:this.getHeader(),
  })
}

/*Supplier APi  */

addSupplier(body:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/suppliers/add`,body,{
    headers:this.getHeader(),
  })
}

getAllSupplier():Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/suppliers/all`,{
    headers:this.getHeader(),
  })
}

getSupplierById(id:string):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/suppliers/${id}`,{
    headers:this.getHeader(),
  })
}

updateSupplier(id:string,body:any):Observable<any>{
  return this.httP.put(`${ApiService.BASE_URL}/suppliers/update/${id}`,body,{
    headers:this.getHeader(),
  })
}

deleteSupplier(id:string):Observable<any>{
  return this.httP.delete(`${ApiService.BASE_URL}/suppliers/delete/${id}`,{
    headers:this.getHeader(),
  })
}


/*Product ENDPOINTS  */

addProduct(formData:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/products/add`,formData,{
    headers:this.getHeader(),
  })
}

updateProduct(formData:any):Observable<any>{
  return this.httP.put(`${ApiService.BASE_URL}/products/update`,formData,{
    headers:this.getHeader(),
  })
}

getAllProducts():Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/products/all`,{
    headers:this.getHeader()
  })
}

getProductById(id:string):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/products/${id}`,{
    headers:this.getHeader(),
  })
}

deleteProduct(id:string):Observable<any>{
  return this.httP.delete(`${ApiService.BASE_URL}/products/delete/${id}`,{
    headers:this.getHeader(),
  })
}

/* TRANSACTION ENDPOINTS  */

purchaseProduct(body:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/transactions/purchase`,body,{
    headers:this.getHeader(),
  })
}

sellProduct(body:any):Observable<any>{
  return this.httP.post(`${ApiService.BASE_URL}/transactions/sell`,body,{
    headers:this.getHeader(),
  })
}

getAllTransactions(searchText:string):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/transactions/all`, {
    params: { searchText: searchText },
    headers: this.getHeader(),
  });
}
getTransactionById(id:string):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/transactions/${id}`,{
    headers:this.getHeader()
  })
}

updateTransactionStatus(id:string,status:string):Observable<any>{
  return this.httP.put(`${ApiService.BASE_URL}/transactions/update/${id}`,JSON.stringify(status),{
    headers: this.getHeader().set("Content-Type","application/json")
  })
}

getTransactionsByMonthAndYear(month:number,year:number):Observable<any>{
  return this.httP.get(`${ApiService.BASE_URL}/transactions/by-month-year`,{
    headers:this.getHeader(),
    params:{
      month:month,
      year:year,
    },
  })
}


  //AUthentication Checker

  logout():void{
    this.clearAuth();
  }

  isAuthenticated():boolean{
    const token=this.getFromStorageAndDecrypt("token");
    return !!token;
  }

  isAdmin():boolean{
    const role=this.getFromStorageAndDecrypt("role");
    return role==="ADMIN";
  }
}
