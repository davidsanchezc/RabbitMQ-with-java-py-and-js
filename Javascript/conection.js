const { Client } = require('pg')

const connectionData = {
    user: 'postgres',
    host: 'localhost',
    database: 'postgres',
    password: 'aurorabel',
    port: 5432,
}
const client = new Client(connectionData)
client.connect()
client.query("INSERT INTO usuarios VALUES ('Julissa','jefa')")
    .then(response => {
        console.log("\n")
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
  