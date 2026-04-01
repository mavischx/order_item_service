
const orderUrl = 'http://localhost:8080/order';
const itemUrl = 'http://localhost:8081/item';

function fetchOrders() {
    fetch(orderUrl, {
        method: 'GET',
        cache: 'no-cache',
        headers: {
            'Accept': 'application/json'
            // Can include the If-None-Match header here
        }
    })
        .then(response => {
            console.log('ETag:', response.headers.get('ETag'));
            console.log("Actual Status:", response.status);
            
            if (response.status === 200) {
                document.getElementById('statusCode').textContent = response.status;
                return response.json();
            }else if (response.status === 304) {
                document.getElementById('statusCode').textContent = response.status;
                document.getElementById('eTag').textContent = response.headers.get('ETag');
                console.log("Data not modified since last fetch. Using cached data.");
            }
             else {
                console.log("Error getting the data..");
            }
            
        })
        .then(data => {
            if (data) {
                // Stringify the data and print into the document
                //document.getElementById('check').textContent = JSON.stringify(data);
                console.log("FETHED DATA: ", data, "order size: ", data.length);
                
                makeOrderTable(data)
            }
        })
        .catch(error => console.error('Error fetching order info:', error));
}


function fetchOneOrder() {
    fetch(orderUrl + "/" + document.getElementById('getOneOrderId').value, {
        method: 'GET',
        cache: 'no-cache',
        headers: {
            'Accept': 'application/json'
        }
    })
        .then(response => {
            console.log('ETag:', response.headers.get('ETag'));
            console.log("Actual Status:", response.status);
            
            if (response.status === 200) {
                document.getElementById('statusCode').textContent = response.status;
                return response.json();
            }else if (response.status === 304) {
                document.getElementById('statusCode').textContent = response.status;
                document.getElementById('eTag').textContent = response.headers.get('ETag');
                console.log("Data not modified since last fetch. Using cached data.");
            }
             else {
                console.log("Error getting the data..");
            }
            
        })
        .then(data => {
            if (data) {
                // Stringify the data and print into the document
                //document.getElementById('check').textContent = JSON.stringify(data);
                console.log("FETHED DATA: ", data, "order size: ", data.length);
                
                editModal(data)
            }
        })
        .catch(error => console.error('Error fetching order info:', error));
}

function editModal(data) {	
    document.getElementById('customerNameModal').innerHTML =  data.orderName;
    document.getElementById('quantityModal').innerHTML = data.quantity;
    document.getElementById('addressModal').innerHTML = data.delAddress;
        document.getElementById('itemIdModal').innerHTML = data.item.itemId;
    document.getElementById('itemNameModal').innerHTML = data.item.menuItem;
     document.getElementById('itemCategoryModal').innerHTML = data.item.category;
      document.getElementById('itemPriceModal').innerHTML = data.item.price;
}

function fetchItems() {
    fetch(itemUrl, {
        method: 'GET',
        cache: 'no-cache',
        headers: {
            'Accept': 'application/json'
            // Can include the If-None-Match header here
        }
    })
        .then(response => {
            console.log('ETag:', response.headers.get('ETag'));
            console.log("Actual Status:", response.status);
            
            if (response.status === 200) {
                //document.getElementById('statusCode').textContent = response.status;
                return response.json();
            }else {
                console.log("Error getting the data..");
            }
            
        })
        .then(data => {
            if (data) {
                // Stringify the data and print into the document
                //document.getElementById('check').textContent = JSON.stringify(data);
                console.log("FETHED DATA: ", data);
                makeItemsTable(data)
            }
        })
        .catch(error => console.error('Error fetching order info:', error));
}

function makeOrderTable(data) {
    console.log("MAKING TABLE: ", data);
    const tbody = document.getElementById('orderTable');
    tbody.textContent = '';
    data.forEach(order => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${order.orderId}</td>
            <td>${order.orderName}</td>
            <td>${order.itemId}</td>
            <td>${order.quantity}</td>
            <td>${order.address}</td>
        `;
        tbody.appendChild(row);
    });
}

function makeItemsTable(data) {
    console.log("MAKING itemS TABLE: ", data);
    const tbody = document.getElementById('itemTable');
    tbody.textContent = '';
    data.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.itemId}</td>
            <td>${item.item}</td>
            <td>${item.category}</td>
            <td>${item.price}</td>
        `;
        tbody.appendChild(row);
    });
}

function addOrder() {
    console.log("ADDING ORDER");
    fetch(orderUrl, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            orderName: document.getElementById('customerNameAdd').value,
            itemId: document.getElementById('itemIdAdd').value,
            quantity: document.getElementById('quantityAdd').value,
            address: document.getElementById('addressAdd').value
        })
    })
        .then(response => {
            document.getElementById('statusCode').textContent = response.status;
            if (response.status === 201) {
                console.log("Order added successfully");

            } else if (response.status === 500) {
                window.alert("item does not exist");
                console.log("Internal server error");
            } else {
                console.log("Error adding order");
            }
        })
        .catch(error => console.error('Error adding order:', error));
}


function editOrder() {
    console.log("EDITING ORDER");
    fetch(orderUrl+"/" + document.getElementById('orderEditId').value, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            orderName: document.getElementById('customerName').value,
            itemId: document.getElementById('itemId').value,
            quantity: document.getElementById('quantity').value,
            address: document.getElementById('address').value
        })
    })
        .then(response => {
            document.getElementById('statusCode').textContent = response.status;
            if (response.status === 201) {
                console.log("Order edited successfully");

            } else {
                console.log("Error editing order");
            }
        })
        .catch(error => console.error('Error editing order:', error));
}

// When the DOM is loaded in memory, call the fetchOrders() function
document.addEventListener("DOMContentLoaded", () => {
    fetchOrders();
    fetchItems();   
});