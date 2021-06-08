$(function(){
   if( $("#content").length){
   ClassicEditor
   .create(document.querySelector("#content"))
   .catch(error=>{
   		console.log(error);
   });
   }
     
});



//first request to serve to create order

// const paymentStart=(data1)=>{
//    console.log("Payment started");
//    console.log(data1);
//    if(data1=='' || data1==null)
//    {
//       alert("amount is required !!")
//       return;
//    }
//  //hum send krege request
//    $.ajax(
//       {
//          url:'/cart/create_order',
//          data:JSON.stringify({data1:data1,info:'order_request'}),
//          contentType:'application/json',
//          Type:'POST',
//          dataType:'json',
//          success:function(response){
//             //invoke when success
//             console.log(response)
//          },
//          error:function(error){
//             //invoke when error
//             console.error()
//             alert("Something went wrong")
//          }
//       }
//    )
// };


