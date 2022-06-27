const { Client } = require('pg')

const connectionData = {
    user: 'postgres',
    host: 'localhost',
    database: 'postgres',
    password: '',
    port: 5432,
}
const client = new Client(connectionData)
client.connect()
client.query('SELECT * FROM usuarios')
    .then(response => {
        console.log(response.rows)
        client.end()
    })
    .catch(err => {
        client.end()
    })
// async function retrieveData() {
//     console.log("A")
//     try {
//       console.log("A")
//       const res = await client.query("SELECT * FROM usuarios");
//       console.log("A")
//       console.log(res.rows);
//     } catch (error) {
//       console.error(error);
//     }
//   }
  
// retrieveData()
  