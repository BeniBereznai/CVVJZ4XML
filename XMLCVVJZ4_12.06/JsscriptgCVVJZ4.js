const fs = require("fs");
    
fs.readFile("./orarendCVVJZ4.json", "utf8", (error, data) => {
  if (error) {
    console.log(error);
    return;
  }
  console.log(JSON.stringify(JSON.parse(data), null, 2));
}); 